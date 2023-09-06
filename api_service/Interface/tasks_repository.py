from api_service.Interface.Interface import ModelRepository


class TaskInterface(ModelRepository):

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