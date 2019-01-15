package com.atguigu.akka.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class AActor(bActorRef : ActorRef) extends Actor{
  var count = 0
  override def receive: Receive = {
    case "start" => {
      println("AActor出招了")
      println("start ok")
      println("我打")
      println(bActorRef)
      bActorRef ! "我打"
    }
    case "我打" => {
      count += 1
      println(s"BActor(黄飞鸿) 挺猛的，看我佛山无影脚 第${this.count}脚")
      Thread.sleep(1000)
      bActorRef ! "我打"
      if(count > 10) {
        println("到此结束")
        context.system.terminate()
      }
    }
  }
}

object AActorAndBActorTest{
  def main(args: Array[String]): Unit = {
    val actorFactory = ActorSystem("actorFactory")
    val bActorRef = actorFactory.actorOf(Props[BActor],"BActor")
    val aActorRef = actorFactory.actorOf(Props(new AActor(bActorRef)),"AActor")
    aActorRef ! "start"
  }
}
