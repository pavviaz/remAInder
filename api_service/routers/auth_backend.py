from fastapi import APIRouter, Depends

from models.user import User
from schemas.user import UserModel, UserCreate, UserUpdate
from services.auth_backend import fastapi_users, auth_backend, current_active_user

api_router = APIRouter(tags=["auth"])
api_router.include_router(
    fastapi_users.get_auth_router(auth_backend), prefix="/auth/jwt", tags=["auth"]
)
api_router.include_router(
    fastapi_users.get_register_router(UserModel, UserCreate),
    prefix="/auth",
)
api_router.include_router(
    fastapi_users.get_reset_password_router(),
    prefix="/auth",
)
api_router.include_router(
    fastapi_users.get_verify_router(UserModel),
    prefix="/auth",
)
api_router.include_router(
    fastapi_users.get_users_router(UserModel, UserUpdate),
    prefix="/users",
    tags=["users"],
)


@api_router.get("/authenticated-route")
async def authenticated_route(user: User = Depends(current_active_user)):
    return {"message": f"Hello {user.email}!"}