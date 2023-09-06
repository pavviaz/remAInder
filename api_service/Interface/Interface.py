from sqlalchemy.ext.asyncio import AsyncSession


class ModelInterface:
    def __init__(self, async_session: AsyncSession):
        self.async_session = async_session

    async def create_execute(self, stmt, values=None):
        try:
            await self.async_session.execute(stmt, values)
            await self.async_session.commit()
        except:
            await self.async_session.rollback()
            await self.async_session.close()
