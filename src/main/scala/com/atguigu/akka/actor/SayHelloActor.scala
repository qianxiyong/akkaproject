package com.atguigu.akka.actor

import akka.actor.{Actor, ActorSystem, Props}

class SayHelloActor extends Actor{
  override def receive: Receive = {
    case "hello" => println("hello")
    case "fish" => println("fish")
    case "cat" => println("cat")
    case "dog" => println("dog")
    /*case "exit" => {
      context.stop(self)
      context.system.terminate()
    }*/
    case _ => println("你说的是什么")
  }
}

object SayHelloActorTest {
  def main(args: Array[String]): Unit = {
    val actorFactory = ActorSystem("actorFactory")
    val sayHelloActorRef = actorFactory.actorOf(Props[SayHelloActor],"SayHelloActor")
    sayHelloActorRef ! "hello"
    sayHelloActorRef ! "fish"
    sayHelloActorRef ! "cat"
    sayHelloActorRef ! "dog"
    sayHelloActorRef ! "goose"
    sayHelloActorRef ! "exit"
  }
}
