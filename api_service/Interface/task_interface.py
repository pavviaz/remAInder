from api_service.interface.interface import ModelInterface


class TaskInterface(ModelInterface):

    async def get(self):
        ...

    async def create(self):
        ...

    async def edit(self):
        ...

    async def get_list(self):
        ...

    async def delete(self):
        ...