import uuid
from fastapi_users import FastAPIUsers
from fastapi_users.authentication import (
    AuthenticationBackend, BearerTransport, JWTStrategy
)


from fastapi.exceptions import HTTPException
from models.user import User
from settings import api_settings

from interface.user_interface import get_user_manager

bearer_transport = BearerTransport(tokenUrl="auth/jwt/login")


def get_jwt_strategy() -> JWTStrategy:
    return JWTStrategy(secret=api_settings.SECURITY_KEY, lifetime_seconds=api_settings.ACCESS_TOKEN_EXPIRE_MINUTES)


auth_backend = AuthenticationBackend(
    name="jwt",
    transport=bearer_transport,
    get_strategy=get_jwt_strategy,
)

fastapi_users = FastAPIUsers[User, uuid.UUID](get_user_manager, [auth_backend])

current_active_user = fastapi_users.current_user(active=True)


async def validator_user_id(user_id):
    current_user = await current_active_user()
    if user_id != current_user.id:
        raise HTTPException(status_code=402)