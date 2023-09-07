from fastapi import APIRouter

from routers.auth_backend import api_router as auth_routers
from settings import api_settings

api_router = APIRouter(prefix=api_settings.URL)
api_router.include_router(auth_routers)
