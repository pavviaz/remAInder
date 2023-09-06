from api_service.Interface.Interface import ModelRepository


class UserInterface(ModelRepository):

    async def get(self):
        ...

    async def create(self):
        ...

    async def edit(self):
        ...