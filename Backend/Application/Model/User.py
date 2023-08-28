from Application.database import database


class User(database.Model):
    __tablename__ = "Users"
    __table_args__ = {'extend_existing': True}

    id = database.Column(database.Integer, primary_key=True)
    username = database.Column(database.String, nullable=False)
    password = database.Column(database.String, nullable=False)
    age = database.Column(database.Integer, nullable=False)
    height = database.Column(database.Integer, nullable=False)
    weight = database.Column(database.Integer, nullable=False)
    gender = database.Column(database.String, nullable=False)
    activity_level = database.Column(database.Integer, database.ForeignKey('ActivityLevels.id'))
    goal = database.Column(database.Integer, database.ForeignKey('Goals.id'))

    def __init__(self, id, username, password, age, height, weight, gender, activity_level, goal):
        self.id = id
        self.username = username
        self.password = password
        self.age = age
        self.height = height
        self.weight = weight
        self.gender = gender
        self.activity_level = activity_level
        self.goal = goal

    def __str__(self):
        return str(self.id) + "," + str(self.username) + "," + str(self.password) + "," + str(self.age) + "," + str(
            self.height) + "," + str(self.weight) + "," + str(self.gender) + "," + str(self.activity_level) + "," + str(
            self.goal)

    def __repr__(self):
        return str(self.id) + "," + str(self.username) + "," + str(self.password) + "," + str(self.age) + "," + str(
            self.height) + "," + str(self.weight) + "," + str(self.gender) + "," + str(self.activity_level) + "," + str(
            self.goal)

    def get_id(self):
        return self.id

    def serialize(self):
        return {
            "id": str(self.id),
            "username": self.username,
            "age": str(self.age),
            "height": str(self.height),
            "weight": str(self.weight),
            "gender": str(self.gender),
            "activityLevel": str(self.activity_level),
            "goal": str(self.goal)
        }
