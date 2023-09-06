from api_service.models import Base
from sqlalchemy import Column, text, Text, sql, Float, SmallInteger, UniqueConstraint, ForeignKey, DateTime
from sqlalchemy.dialects.postgresql import UUID
import uuid
import datetime


class Task(Base):
    __tablename__ = 'task'

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4,
                server_default=text("uuid_generate_v4()"))

    datetime = Column(DateTime(True), nullable=False, server_default=sql.func.now(),
                      default=datetime.datetime.utcnow)
    description = Column(Text, nullable=False)

    user_id = Column(UUID(as_uuid=True), ForeignKey('auth.user.id'), nullable=True)

    duration = Column(SmallInteger, nullable=True)
    embedding = Column(Float, nullable=True)

    __table_args__ = (
        UniqueConstraint('description', 'datetime', 'user_id', name='unique_desc_date_user'),
        {'schema': 'tasks'}
    )
