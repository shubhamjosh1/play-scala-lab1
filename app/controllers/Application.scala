package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject
import play.api.mvc._
import java.sql.{Connection,DriverManager}
import play.api.Configuration
import scala.collection.mutable.ListBuffer


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */



@Singleton
class Application @Inject()(cc: ControllerComponents) (playconfiguration: Configuration) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  val url = playconfiguration.get[String]("db.default.url")
  val driver = playconfiguration.get[String]("db.default.driver")
  val username = playconfiguration.get[String]("db.default.user")
  val password = playconfiguration.get[String]("db.default.password")

  val conn = DriverManager.getConnection(url,username,password)

  def index() = Action {

    implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }


  def doLogin = Action { implicit request =>

    println("post")
    val loginRequest = loginForm.bindFromRequest.get
    // access "default" database

    val statement = conn.createStatement
    var query="insert into play(username,email) values('"+loginRequest.username+"','"+loginRequest.email+"')"

    println("query",query)
    val rs = statement.executeUpdate(query)


    Ok("information saved succesfully for"+s"user '${loginRequest.username}' with email '${loginRequest.email}'")
  }


  def getUsers = Action { implicit request: Request[AnyContent] =>

    val statement = conn.createStatement
    val rs = statement.executeQuery("SELECT username,email from play")

    var users = new ListBuffer[String]()

    var emailadd = new ListBuffer[String]()

    while (rs.next) {
      println("")
      val username = rs.getString("username")
      users+=username

      val email = rs.getString("email")
      emailadd+=email

      println("username = %s, password = %s".format(users.toList,emailadd.toList))
    }


      Ok(views.html.index())
  }

  /*An extractor object is an object with an unapply method. Whereas the apply method is like a constructor
  which takes arguments and creates an object, the unapply takes an object and tries to give back the arguments.
  */
  def loginForm = Form(mapping("username" -> text, "email" -> text)
  (LoginRequest.apply)(LoginRequest.unapply))

  // define a case class which contains the elements you want in the form.
  // Here we want to capture the username and password of a user, so we create a LoginRequest object

  case class LoginRequest(username:String, email:String)

}
