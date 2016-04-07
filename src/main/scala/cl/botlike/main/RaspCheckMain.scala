package cl.botlike.main

import akka.actor.{ActorSystem, Props}
import cl.botlike.actor.PublishActor
import cl.botlike.message.Motion
import com.pi4j.io.gpio.event.{GpioPinDigitalStateChangeEvent, GpioPinListenerDigital}
import com.pi4j.io.gpio.{GpioFactory, PinPullResistance, PinState, RaspiPin}

/**
  * Created by cmontecinos on 02-04-16.
  */
object RaspCheckMain{

  def main(args: Array[String]) {
    println("starting actor system...");
    val motion = GpioFactory.getInstance();
    val sMotion = motion.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);

    val system = ActorSystem("raspCheck");
    val publicActor = system.actorOf(Props[PublishActor], "publish");

    sMotion.addListener(new GpioPinListenerDigital {
      println("starting sensor listener");
      override def handleGpioPinDigitalStateChangeEvent(event: GpioPinDigitalStateChangeEvent): Unit = {
        event.getState match {
          case PinState.HIGH => {
            publicActor ! new Motion(true);
          }
          case other => println("no motion detected");
        }
      }
    })
  }

}
