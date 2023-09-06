from api_service.interface.interface import ModelInterface


class UserInterface(ModelInterface):

    async def get(self):
        ...

    async def create(self):
        ...

    async def edit(self):
        ...