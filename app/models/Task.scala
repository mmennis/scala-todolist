package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

import play.Logger._

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 6/21/13
 * Time: 5:22 PM
 * The task object
 */

case class Task(id: Long, label: String)

object Task {

  val task = {
    get[Long]("id") ~
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

  def all: List[Task] = DB.withConnection { implicit c =>
    info("Querying all tasks from database ...")
    SQL("select * from task").as(task *)
  }

  def create(label: String) {
    info("Inserting %s into tasks table ...".format(label))
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate
    }
  }

  def delete(id: Long) {
    info("Removing task %d from tasks table ...".format(id))
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate
    }
  }

}
