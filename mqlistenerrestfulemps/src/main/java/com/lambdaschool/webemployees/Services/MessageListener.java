package com.lambdaschool.webemployees.Services;

import com.lambdaschool.webemployees.WebemployeesApplication;
import com.lambdaschool.webemployees.model.MessageDetail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener
{
    @RabbitListener(queues = WebemployeesApplication.QUEUE_NAME_HIGH)
    public void receiveMessage(MessageDetail msg)
    {
        System.out.println("Received High Queue message {" + msg.toString() + "}");
    }

    @RabbitListener(queues = WebemployeesApplication.QUEUE_NAME_LOW)
    public void receiveLowMessage(MessageDetail msg)
    {
        System.out.println("Received Low  Queue message {" + msg.toString() + "}");
    }
}
