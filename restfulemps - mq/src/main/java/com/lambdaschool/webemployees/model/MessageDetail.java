package com.lambdaschool.webemployees.model;

import java.io.Serializable;

public class MessageDetail implements Serializable
{
    private String text;
    private int priority;
    private boolean secret;

    public MessageDetail()
    {
    }

    public MessageDetail(String text,
                         int priority,
                         boolean secret)
    {
        this.text = text;
        this.priority = priority;
        this.secret = secret;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public boolean isSecret()
    {
        return secret;
    }

    public void setSecret(boolean secret)
    {
        this.secret = secret;
    }

    @Override
    public String toString()
    {
        return "MessageDetail{" + "text='" + text + '\'' + ", priority=" + priority + ", secret=" + secret + '}';
    }
}
