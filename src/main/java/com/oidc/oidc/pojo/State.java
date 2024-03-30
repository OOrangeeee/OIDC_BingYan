package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("states")
public class State {
    @TableField("id")
    private Integer id;
    @TableField("state")
    private String state;

    public State() {
    }

    public State(Integer id, String state) {
        this.id = id;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state1 = (State) o;
        return Objects.equals(id, state1.id) &&
                Objects.equals(state, state1.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state);
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", state='" + state + '\'' +
                '}';
    }
}
