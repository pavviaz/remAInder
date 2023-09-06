from pydantic import BaseModel, Field
import uuid
import datetime


class UserModel(BaseModel):
    id: uuid.UUID = Field(default_factory=uuid.uuid4)

    datetime: datetime.datetime
    description: str

    user_id: uuid.UUID | None = Field(default_factory=uuid.uuid4)

    duration: int | None = Field(default=None)
    embedding: float | None = Field(default=None)

    class Config:
        orm_mode = True
