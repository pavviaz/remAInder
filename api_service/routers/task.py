from uuid import UUID
from datetime import date as date_dt

from fastapi import APIRouter, Path, Depends, UploadFile
from sqlalchemy.ext.asyncio import AsyncSession

from postgres import get_async_session
from schemas.tasks import TaskRead, TaskCreate, TaskUpdate
from services.tasks import TaskService

from fastapi.exceptions import HTTPException
from models.user import User
from services.auth_backend import current_active_user

api_router = APIRouter(tags=["tasks"], prefix='/task')


@api_router.get("/{task_id}")
async def get_task(task_id: UUID = Path(),
                   user: User = Depends(current_active_user),
                   session: AsyncSession = Depends(get_async_session)):
    service = TaskService(session)
    return await service.get(task_id, TaskRead)


@api_router.post("/get_tasks")
async def get_tasks(date: date_dt, user_id: UUID,
                    user: User = Depends(current_active_user),
                    session: AsyncSession = Depends(get_async_session)):
    service = TaskService(session)
    return await service.get_by_date_and_user(user_id, date, TaskRead)


@api_router.put("/")
async def put_task(task: TaskUpdate,
                   user: User = Depends(current_active_user),
                   session: AsyncSession = Depends(get_async_session)):
    service = TaskService(session)
    return await service.update(task, TaskRead)


@api_router.post("/")
async def post_task(task: TaskCreate,
                    user: User = Depends(current_active_user),
                   session: AsyncSession = Depends(get_async_session)):
    service = TaskService(session)
    result = await service.create(task)
    return result if result else HTTPException(status_code=500)


@api_router.delete("/{task_id}")
async def delete_task(task_id: UUID = Path(),
                      user: User = Depends(current_active_user),
                      session: AsyncSession = Depends(get_async_session)):
    service = TaskService(session)
    status = await service.delete(task_id)
    return {'status': status} if status else HTTPException(status_code=500)


@api_router.post("/upload_audio")
async def upload_audio(file: UploadFile, user_id: UUID,
                       user: User = Depends(current_active_user),
                       session: AsyncSession = Depends(get_async_session)):
    service = TaskService(session)
    return await service.upload_audio(user_id, file)
