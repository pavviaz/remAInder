from fastapi import Depends
from sqlalchemy.ext.asyncio import AsyncSession

from base_interface.interface import ModelInterface
from fastapi_users.db import SQLAlchemyUserDatabase

from models.user import User
from postgres import get_async_session
from services.user_manager import UserManager


async def get_user_db(session: AsyncSession = Depends(get_async_session)):
    interface = UserInterface(session)
    yield await interface.get_user_db()


async def get_user_manager(user_db: SQLAlchemyUserDatabase = Depends(get_user_db)):
    yield UserManager(user_db)


class UserInterface(ModelInterface):

    async def get_user_db(self):
        return SQLAlchemyUserDatabase(self.async_session, User)

    async def get(self):
        ...

    async def create(self):
        ...

    async def edit(self):
        ...




