from sqlalchemy.ext.asyncio import AsyncSession


class ModelInterface:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def create_execute(self, stmt, values=None):
        try:
            await self.session.execute(stmt, values)
            await self.session.commit()
        except:
            await self.session.rollback()
