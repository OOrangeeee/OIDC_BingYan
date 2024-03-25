package com.oidc.oidc.service.impl.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.ClientMapper;
import com.oidc.oidc.pojo.Client;
import com.oidc.oidc.service.interfaces.client.ClientRegisterService;
import com.oidc.oidc.service.interfaces.tools.UrlValidationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 晋晨曦
 */
@Service
public class ClientRegisterServiceImpl implements ClientRegisterService {

    private final ClientMapper clientMapper;

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    private final UrlValidationService urlValidationService;

    private static final Logger logger = LoggerFactory.getLogger(ClientRegisterServiceImpl.class);

    public ClientRegisterServiceImpl(ClientMapper clientMapper, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender, UrlValidationService urlValidationService) {
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.clientMapper = clientMapper;
        this.urlValidationService = urlValidationService;
    }

    @Override
    public Map<String, String> getClientRigister(String clientName, String clientPassword, String clientRedirectUrl, String clientEmail) {
        Map<String, String> map = new HashMap<>();
        if (clientName == null) {
            map.put("error_message", "(backend error)客户端用户名不能为空");
            logger.error("客户端用户名不能为空");
            return map;
        }
        if (clientPassword == null) {
            map.put("error_message", "(backend error)客户端密码不能为空");
            logger.error("客户端密码不能为空");
            return map;
        }
        if (clientRedirectUrl == null) {
            map.put("error_message", "客户端回调地址不能为空");
            logger.info("客户端回调地址不能为空");
            return map;
        }
        if (clientEmail == null) {
            map.put("error_message", "客户端邮箱不能为空");
            logger.info("客户端邮箱不能为空");
            return map;
        }
        if (clientName.length() > 1000) {
            map.put("error_message", "(backend error)客户端用户名过长");
            logger.error("客户端用户名过长");
            return map;
        }
        if (clientPassword.length() > 1000) {
            map.put("error_message", "(backend error)客户端密码过长");
            logger.error("客户端密码过长");
            return map;
        }
        if (clientRedirectUrl.length() > 1000) {
            map.put("error_message", "客户端回调地址过长");
            logger.info("客户端回调地址过长");
            return map;
        }
        if (!urlValidationService.isValidHttpOrHttpsUrl(clientRedirectUrl)) {
            map.put("error_message", "客户端回调地址格式错误");
            logger.info("客户端回调地址格式错误");
        }

        QueryWrapper<Client> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("client_redirection_URL", clientRedirectUrl);
        List<Client> clients1 = clientMapper.selectList(queryWrapper1);
        if (!clients1.isEmpty()) {
            map.put("error_message", "客户端回调地址已存在");
            logger.info("客户端回调地址已存在");
            return map;
        }


        QueryWrapper<Client> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("client_name", clientName);

        List<Client> clients2 = clientMapper.selectList(queryWrapper2);
        for (Client client : clients2) {
            if (!client.isClientIsActive()) {
                clientMapper.deleteById(client.getId());
            } else {
                map.putIfAbsent("error_message", "（backend error）client随机ID重复");
                logger.error("client随机ID重复");
                return map;
            }
        }


        Integer id = clientMapper.findMaxId();
        if (id == null) {
            id = 0;
        }

        id++;

        Client newClient = new Client(id, clientName, passwordEncoder.encode(clientPassword), clientRedirectUrl, clientEmail, false, null, null);
        String newClientConfirmationToken = UUID.randomUUID().toString();
        newClientConfirmationToken = id + newClientConfirmationToken + id * id % 23 + id * id % 17;
        newClient.setClientConfirmationToken(newClientConfirmationToken);
        newClient.setClientIsActive(false);
        clientMapper.insert(newClient);

        Map<String, String> sendMap = sendConfirmationEmail(newClient);

        if ("发送频率太高，请等待五分钟".equals(sendMap.get("send_email_error_message"))) {
            map.put("error_message", "发送频率太高，请等待五分钟");
        } else if ("发送成功".equals(sendMap.get("send_email_error_message"))) {
            map.put("error_message", "注册成功，请前往邮箱激活账号");
        } else {
            map.put("error_message", "邮件发送失败，请检查邮箱配置");
            logger.info("邮件发送失败");
        }
        return map;

    }

    @Override
    public Map<String, String> confirmClientAccount(String clientConfirmationToken) {
        Map<String, String> map = new HashMap<>();

        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_confirmation_token", clientConfirmationToken);
        Client client = clientMapper.selectOne(queryWrapper);
        if (client == null) {
            map.put("error_message", "(backend error)无效的确认令牌");
            logger.error("客户端激活令牌错误");
            return map;
        }

        client.setClientIsActive(true);
        clientMapper.updateById(client);

        map.put("error_message", "账户激活成功");
        return map;
    }

    private Map<String, String> sendConfirmationEmail(Client client) {
        Map<String, String> map = new HashMap<>();
        Date now = new Date();
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_email", client.getClientEmail()).orderByDesc("client_last_email_sent_time");
        List<Client> clients = clientMapper.selectList(queryWrapper);

        Client clientWithLatestEmailTime = clients.isEmpty() ? null : clients.get(0);

        if (clientWithLatestEmailTime != null && clientWithLatestEmailTime.getClientLastEmailSentTime() != null) {
            long diff = now.getTime() - clientWithLatestEmailTime.getClientLastEmailSentTime().getTime();
            long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (diffMinutes < 5) {
                map.put("send_email_error_message", "发送频率太高，请等待五分钟");
                return map;
            }
        }
        for (Client clientTmp : clients) {
            clientTmp.setClientLastEmailSentTime(now);
            clientMapper.updateById(clientTmp);
        }

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(client.getClientEmail());
            mailMessage.setFrom("Jin0714ForProject@163.com");
            mailMessage.setSubject("看看我！你的客户端账户需要激活！");
            mailMessage.setText("想激活你的客户端账号？点击这里 :\n " + "http://localhost:714/client/confirm?token=" + client.getClientConfirmationToken() + "\n这是你的客户端用户id：\n" + client.getClientName() + "\n这是你的客户端密码:\n" + client.getClientPassword() + "\n请注意，为保证安全，此id和密码只会出现在此邮件中，请勿泄露给他人。");
            javaMailSender.send(mailMessage);
            map.put("send_email_error_message", "发送成功");
        } catch (Exception e) {
            map.put("send_email_error_message", "发送失败");
        }

        return map;
    }
}
