from datetime import date as date_dt
from typing import Type
from uuid import UUID

from CacheToolsUtils import RedisCache
from asyncache import cached
from fastapi import UploadFile
from pydantic import parse_obj_as

from interface.task_interface import TaskInterface
from schemas.tasks import TaskModel, TaskRead, TaskCreate

from schemas.tasks import TaskId

from schemas.tasks import TaskUpdate
# import cachetools
# from redis_cache import redis


class TaskService(TaskInterface):

    async def get(self, id: UUID, model: Type[TaskModel] | Type[TaskRead] = Type[TaskModel]) -> TaskModel | TaskRead:
        task = model.from_orm(await self._get(id))
        return task

    async def get_by_date_and_user(self, user_id: UUID, date: date_dt = None,
                                   model: Type[TaskModel] | Type[TaskRead] = Type[TaskModel]
                                   ) -> list[TaskModel | TaskRead]:

        tasks = await self._get_by_date_and_user(user_id, date)
        return parse_obj_as(list[model], tasks)

    async def create(self, task_to_create: TaskCreate) -> TaskId | None:
        embedding = None  #TODO вызов расчета embedding

        task_id = await self._create(TaskModel(**task_to_create.dict()))
        if not task_id:
            return None
        return TaskId(id=task_id)

    async def create_all(self, tasks_to_create: list[TaskCreate]) -> list[int]:
        embeddings = None  # TODO вызов расчета embeddings для множества моделей

        tasks_to_create = parse_obj_as(list[TaskModel], tasks_to_create)

        task_id = await self._create_all(tasks_to_create)
        return task_id

    async def update(self, task_to_update: TaskUpdate, model: Type[TaskModel] | Type[TaskRead] = Type[TaskModel]
                     ) -> TaskModel | TaskRead:

        task = await self._update(task_to_update)
        return model.from_orm(task)

    async def upload_audio(self, user_id, file: UploadFile):
        ... # TODO отправка таски на расчет моделей
        print(file.filename)
        return {'task_id': 'task_celery_id', 'name': file.filename, 'user_id': user_id}