
from flask_cors import CORS
from Application.app import app
from Application.main_routes import main_api

app.register_blueprint(main_api)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'

if __name__ == "__main__":
    app.run(debug=True)