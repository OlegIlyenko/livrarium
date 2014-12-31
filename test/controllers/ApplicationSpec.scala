package controllers

import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import com.mohiva.play.silhouette.test._
import fixtures.UserFixture
import globals.TestGlobal
import helpers.LivrariumSpecification
import org.specs2.execute.AsResult
import org.specs2.matcher.{FileMatchers, ThrownMessages}
import org.specs2.specification.AroundExample
import play.api.test._

class ApplicationSpec extends LivrariumSpecification with FileMatchers with AroundExample with ThrownMessages {

  /**
   * This automatically handles up and down evolutions at the beginning and at the end of a spec respectively
   */
  def around[T: AsResult](t: => T) = {
    val app = FakeApplication(withGlobal = Some(TestGlobal), additionalConfiguration = inMemoryDatabase())
    running(app) {
      await(UserFixture.initFixture())

      AsResult(t)
    }
  }

  "Application controller" should {

    "show login page if user is not authenticated" in {
      // Given
      // Authenticated with other user than the one which is stored in current environment
      val request = FakeRequest().withAuthenticator[SessionAuthenticator](UserFixture.otherUserLoginInfo)

      val applicationController = new Application

      val expectedHtml = contentAsString(views.html.index())

      // When
      val result = applicationController.index()(request)

      // Then
      status(result) mustEqual OK
      contentType(result) must beSome("text/html")
      contentAsString(result) must beEqualTo(expectedHtml)
    }

    "show redirect to Cloud index page if user is authenticated" in {
      // Given
      val request = FakeRequest().withAuthenticator[SessionAuthenticator](UserFixture.testUserLoginInfo)

      val applicationController = new Application

      // When
      val result = applicationController.index()(request)

      // Then
      status(result) mustEqual SEE_OTHER
      redirectLocation(result) must beSome(routes.Cloud.index().toString())
    }
  }
}