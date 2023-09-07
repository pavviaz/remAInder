from pydantic import BaseModel, Field
import uuid
import datetime as datetime_dt


class TaskModel(BaseModel):
    id: uuid.UUID = Field(default_factory=uuid.uuid4)

    datetime: datetime_dt.datetime
    description: str

    user_id: uuid.UUID = Field(default_factory=uuid.uuid4)

    duration: int | None = Field(default=None)
    embedding: float | None = Field(default=None)

    class Config:
        orm_mode = True
        from_attributes = True


class TaskRead(BaseModel):
    id: uuid.UUID = Field(default_factory=uuid.uuid4)

    datetime: datetime_dt.datetime
    description: str

    user_id: uuid.UUID = Field(default_factory=uuid.uuid4)
    duration: int | None = Field(default=None)

    class Config:
        orm_mode = True
        from_attributes = True


class TaskCreate(BaseModel):
    id: uuid.UUID = Field(default_factory=uuid.uuid4)

    datetime: datetime_dt.datetime
    description: str

    user_id: uuid.UUID = Field(default_factory=uuid.uuid4)
    duration: int | None = Field(default=None)

    class Config:
        orm_mode = True
        from_attributes = True


class TaskUpdate(BaseModel):
    id: uuid.UUID = Field(default_factory=uuid.uuid4)

    datetime: datetime_dt.datetime | None = Field(default=None)
    description: str | None = Field(default=None)

    user_id: uuid.UUID | None = Field(default=None)
    duration: int | None = Field(default=None)

    class Config:
        orm_mode = True
        from_attributes = True


class TaskId(BaseModel):
    d: uuid.UUID = Field(default_factory=uuid.uuid4)