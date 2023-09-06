from pydantic import BaseModel, Field
import uuid


class UserModel(BaseModel):
    id: uuid.UUID = Field(default_factory=uuid.uuid4)
    email: str
    password: str
    name: str = Field(default=None)

    class Config:
        orm_mode = True