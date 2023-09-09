from pydantic import EmailStr
from schemas.user import UserModel, UserCreate, UserUpdate
from services.auth_backend import fastapi_users, auth_backend, current_active_user

from fastapi import APIRouter, Depends, HTTPException, Request, status, Body

from fastapi_users import exceptions, schemas, BaseUserManager, models
from fastapi_users.router.common import ErrorCode, ErrorModel

from services.user_manager import UserManager

from models.user import User

from base_interface.user_interface import get_user_manager


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


@api_router.get("/auth/verify")
async def authenticated_route(user: User = Depends(current_active_user)):
    return {"user_id": user.id}

