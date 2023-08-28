
def deploy():
    from Application.app import app
    from Application.database import database
    from Application.Model.ActivityLevel import ActivityLevel
    from Application.Model.Goal import Goal
    from Application.Model.User import User

    app.app_context().push()
    database.create_all()

    database.session.add(Goal(1,"Lose Weight"))
    database.session.add(Goal(2, "Maintain Weight"))
    database.session.add(Goal(3, "Gain Weight"))
    database.session.add(Goal(4, "Gain Muscle"))

    database.session.add(ActivityLevel(1, "Sedentary"))
    database.session.add(ActivityLevel(2, "Lightly Active"))
    database.session.add(ActivityLevel(3, "Active"))
    database.session.add(ActivityLevel(4, "Very Active"))

    database.session.add(User(1, "yes", "no", 1, 2, 3, "M", 1, 2))


    database.session.commit()


deploy()
