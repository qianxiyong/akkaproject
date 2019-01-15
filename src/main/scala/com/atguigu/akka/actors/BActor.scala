package com.atguigu.akka.actors

import akka.actor.Actor

class BActor extends Actor{
  var count = 0
  override def receive: Receive = {
    case "我打" => {
      count += 1
      println(s"BActor(乔峰) 挺猛的，看我降龙十八掌 第${this.count}掌")
      Thread.sleep(1000)
      sender() ! "我打"
      if(count > 10) {
        println("到此结束")
        context.system.terminate()
      }
    }
  }
}
