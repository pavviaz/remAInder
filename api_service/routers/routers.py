from fastapi import APIRouter

from api_service.routers.auth_backend import api_router as auth_routers
from api_service.routers.task import api_router as task_routers
from api_service.settings import api_settings

api_router = APIRouter(prefix=api_settings.URL)
api_router.include_router(auth_routers)
api_router.include_router(task_routers)
