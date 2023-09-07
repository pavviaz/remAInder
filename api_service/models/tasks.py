from models import Base
from sqlalchemy import Column, text, Text, sql, SmallInteger, UniqueConstraint, ForeignKey, DateTime
from sqlalchemy.dialects.postgresql import JSONB
from sqlalchemy.dialects.postgresql import UUID
import uuid
import datetime

from sqlalchemy.orm import relationship


class Task(Base):
    __tablename__ = 'task'

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4,
                server_default=text("gen_random_uuid()"))

    datetime = Column(DateTime(True), nullable=False, server_default=sql.func.now(),
                      default=datetime.datetime.utcnow)
    description = Column(Text, nullable=False)

    user_id = Column(UUID(as_uuid=False), ForeignKey('auth.user.id'), nullable=True)

    duration = Column(SmallInteger, nullable=True)
    embedding = Column(JSONB, nullable=True)

    user = relationship('User', foreign_keys=[user_id], back_populates='tasks')

    __table_args__ = (
        UniqueConstraint('description', 'datetime', 'user_id', name='unique_desc_date_user'),
        {'schema': 'tasks'}
    )
