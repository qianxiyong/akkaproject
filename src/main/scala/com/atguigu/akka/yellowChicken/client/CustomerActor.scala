package com.atguigu.akka.yellowChicken.client

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.atguigu.akka.yellowChicken.common.{ClientMessage, ServerMessage}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

class CustomerActor(serverHost:String,serverPort:Int) extends Actor{

  var yellowChickenServerRef : ActorSelection = _

  override def preStart():Unit={
    println("preStart...")
    yellowChickenServerRef = context.actorSelection(s"akka.tcp://Server@$serverHost:$serverPort/user/YellowChickenServer")
  }

  override def receive: Receive = {
    case "start" => {
      println("客户端启动，你有问题可以咨询")
    }
    case mes : String => {
      println(mes)
      println(yellowChickenServerRef)
      yellowChickenServerRef ! ClientMessage(mes)
    }
    case ServerMessage(mes) =>{
      println(s"小黄鸡回复$mes")
    }
  }
}

object CustomerActorApp extends App {

  val (clientHost,clientPort,serverHost,serverPort) = ("127.0.0.1",9998,"127.0.0.1",9999)
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname=$clientHost
       |akka.remote.netty.tcp.port=$clientPort
     """.stripMargin
  )
  val clientSystem = ActorSystem("Client",config)
  val customerActorRef: ActorRef = clientSystem.actorOf(Props(new CustomerActor(serverHost,serverPort)),"CustomerActor")
  customerActorRef ! "start"
  while(true) {
    val mes = StdIn.readLine()
    customerActorRef ! mes
  }
}