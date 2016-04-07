package cl.botlike.actor

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.stream.ActorMaterializer
import akka.util.ByteString
import cl.botlike.message.Motion

/**
  * Created by cmontecinos on 02-04-16.
  */

class PublishActor extends Actor{
  implicit val materializer = ActorMaterializer();

  val http = Http(context.system)
  val hds= List(RawHeader("Accept", "application/json"),
                RawHeader("Authorization", "api-key cb3b6140-99af-4eb5-9d37-cac1246ac884"));
  val url = "https://realtime.opensensors.io/v1/topics/%2Fusers%2Fbotlike%2Fmotion?client-id=3083&password=<device-password>";

  val jsonRequest = ByteString(
    s"""
       |{
       |    "data":"movimiento detectado"
       |}
        """.stripMargin)

  def receive  = {

    case Motion(true) => {
      println("Moviemiento detectado, enviando requesst");
      val entityy =  HttpEntity(MediaTypes.`application/json`, jsonRequest);
      val req = HttpRequest(HttpMethods.POST,
                            uri = url,
                            headers = hds,
                            entity = entityy);
      http.singleRequest(request = req);
    }
    case _ => println("Error");
  }

}
