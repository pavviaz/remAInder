from datetime import datetime, date as date_dt
from uuid import UUID

from sqlalchemy import select, insert, update, delete

from api_service.interface.interface import ModelInterface
from api_service.models.tasks import Task
from api_service.schemas.tasks import TaskModel

from api_service.schemas.tasks import TaskUpdate


class TaskInterface(ModelInterface):

    async def _get(self, id: UUID) -> Task:
        stmt = select(Task).where(Task.id == id)
        task = (await self.execute(stmt)).scalars().one()
        return task

    async def _get_by_date_and_user(self, user_id: UUID, date: date_dt = None) -> list[Task]:
        stmt = select(Task).where(Task.user_id == user_id)
        if date:
            start = datetime.combine(date=date, time=datetime.min.time())
            end = datetime.combine(date=date, time=datetime.max.time())
            stmt.where(Task.datetime.between(start, end))
        tasks = (await self.execute(stmt)).scalars().all()
        return tasks

    async def _create(self, task_to_create: TaskModel) -> int:
        stmt = insert(Task).values(**task_to_create.dict(exclude_unset=True)).returning(Task.id)
        result = await self.commit_execute(stmt)
        task_id = result.scalars().one() if result else None
        return task_id

    async def _create_all(self, tasks_to_create: list[TaskModel]) -> list[int]:
        stmt = insert(Task).returning(Task.id)
        values = [task.dict(exclude_unset=True) for task in tasks_to_create]
        task_ids = (await self.commit_execute(stmt, values)).scalars().all()
        return task_ids

    async def _update(self, task_to_update: TaskUpdate) -> Task:
        print(task_to_update)
        stmt = update(Task).where(Task.id == task_to_update.id)\
            .values(**task_to_update.dict(exclude_unset=True, exclude={'id'})).returning(Task)

        task = (await self.commit_execute(stmt)).scalars().first()
        return task

    async def delete(self, id: UUID) -> bool:
        stmt = delete(Task).where(Task.id == id).returning(Task.id)
        task = (await self.commit_execute(stmt)).scalars().first()

        return bool(task)
