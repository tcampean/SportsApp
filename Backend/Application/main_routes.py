import sqlalchemy
from flask import Blueprint, request, jsonify, make_response
from flask_cors import cross_origin

from Application.Model.Metadata import Metadata
from Application.Model.Response import Response
from Application.Model.User import User
from Application.database import database

main_api = Blueprint('main_api', __name__)


@cross_origin()
@main_api.route('/register/', methods=["GET", "POST", "OPTIONS"])
def register():
    print("GOt here")
    if request.method == "POST":
        new_user = request.json
        print(new_user)
        if not User.query.filter_by(username=new_user["username"]).first():
            id = database.session.query(sqlalchemy.func.max(User.id)).scalar()
            print(User(id + 1, new_user["username"], new_user["password"], new_user["age"], new_user["height"],
                       new_user["weight"], new_user["gender"], new_user["activity_level"], new_user["goal"]))
            database.session.add(
                User(id + 1, new_user["username"], new_user["password"], new_user["age"], new_user["height"],
                     new_user["weight"], new_user["gender"], new_user["activity_level"], new_user["goal"]))
            database.session.commit()
            response = make_response("Your account has been created")
            response.data = "Your account has been created"
            response.status_code = 200
            return _corsify_actual_response(response)
        else:
            response = make_response("There is already an account with that username!")
            response.status_code = 400
            return _corsify_actual_response(response)

    elif request.method == "OPTIONS":
        return _build_cors_preflight_response()


@cross_origin()
@main_api.route('/login/', methods=["GET"])
def login():
    print("GOT HERE")
    print(request.method)
    if request.method == "GET":
        print("GOT HERE 2")
        print(request.args)
        print(request.args["username"])
        print(request.args["password"])
        user_info = {
            "username": request.args["username"],
            "password": request.args["password"]
        }
        print("GOT HERE 3")
        user = User.query.filter_by(username=user_info["username"]).first()
        print(user)
        if not user:
            print("USER NO FOUND")
            response = make_response("Wrong Credentials")
            response.status_code = 400
            return response
        else:
            if user_info["password"] == user.password:
                response = make_response(jsonify(
                    {
                        'age': str(user.age),
                        'height': str(user.height),
                        'weight': str(user.weight),
                        'gender': str(user.gender),
                        'activity_level': str(user.activity_level),
                        'goal': str(user.goal),
                    }
                ), 200)
                return response
            else:
                print("WRONG CREDENTIALS")
                response = make_response("Wrong Credentials")
                response.data = jsonify(
                    {
                        'age': str(user['age']),
                        'height': str(user['height']),
                        'weight': str(user['weight']),
                        'gender': str(user['gender']),
                        'activity_level': str(user['activity_level']),
                        'goal': str(user['goal']),
                    }
                )
                response.status_code = 400
                return response


@cross_origin()
@main_api.route('/updateWeight/', methods=["PUT", "OPTIONS"])
def updateWeight():
    if request.method == "PUT":
        query = request.args
        user = User.query.filter_by(username=query.get("username")).first()
        user.weight = query.get("weight")
        database.session.commit()
        return _corsify_actual_response(jsonify(request.json)), 200

    elif request.method == "OPTIONS":
        return _build_cors_preflight_response()


@cross_origin()
@main_api.route('/test/', methods=["GET"])
def test():
    if request.method == "GET":
        response = Response(Metadata(1), User.query.all())
        return jsonify(response.serialize())


def _build_cors_preflight_response():
    response = make_response()
    response.headers.add("Access-Control-Allow-Origin", "*")
    response.headers.add('Access-Control-Allow-Headers', "*")
    response.headers.add('Access-Control-Allow-Methods', "*")
    return response


def _corsify_actual_response(response):
    response.headers.add("Access-Control-Allow-Origin", "*")
    return response
