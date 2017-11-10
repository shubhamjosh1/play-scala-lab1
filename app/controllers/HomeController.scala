package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def doLogin = Action { implicit request =>
    val loginRequest = loginForm.bindFromRequest.get
    Ok(s"username: '${loginRequest.username}', password: '${loginRequest.password}'")
  }

  def loginForm = Form(mapping("username" -> text, "password" -> text)
  (LoginRequest.apply)(LoginRequest.unapply))

  case class LoginRequest(username:String, password:String)

}
