package com.atguigu.akka.yellowChicken.server

import akka.actor
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.atguigu.akka.yellowChicken.common.{ClientMessage, ServerMessage}
import com.typesafe.config.ConfigFactory

class YellowChickenServer extends Actor {
  override def receive: Receive = {
    case "start" => println("服务器启动了，在9999端口监听...")
    case ClientMessage(mes) => {
      println("接收到客户咨询的问题"+mes)
      mes match {
        case "大数据学费" => sender() ! ServerMessage("学费是2wrmb")
        case "地址" => sender() ! ServerMessage("深圳宝安区xxxx路")
        case "学习内容" => sender() ! ServerMessage("前端 Javaee 大数据..")
        case _ => sender() !ServerMessage("你说的什么")
      }
    }
  }
}

object YellowChickenServerApp extends App {
  val serverHost = "127.0.0.1"
  val serverPort = 9999
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$serverHost
       |akka.remote.netty.tcp.port=$serverPort
     """.stripMargin
  )
  val serverActorFactory = ActorSystem("Server",config)
  val yellowChickenServerRef: ActorRef = serverActorFactory.actorOf(Props[YellowChickenServer],"YellowChickenServer")
  yellowChickenServerRef ! "start"
}
