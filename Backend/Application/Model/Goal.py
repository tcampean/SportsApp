from Application.database import database


class Goal(database.Model):
    __tablename__ = "Goals"
    __table_args__ = {'extend_existing': True}

    id = database.Column(database.Integer, primary_key=True)
    name = database.Column(database.String, nullable=False)

    def __init__(self, id, name):
        self.id = id
        self.name = name

    def __str__(self):
        return str(self.id) + "," + str(self.name)

    def __repr__(self):
        return str(self.id) + "," + str(self.name)

    def get_id(self):
        return self.id

    def serialize(self):
        return {
            "id": str(self.id),
            "name": self.name,
        }
