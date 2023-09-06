from api_service.models import Base
from sqlalchemy import Column, text, Text, String, UniqueConstraint
from sqlalchemy.dialects.postgresql import UUID
import uuid
from sqlalchemy.orm import relationship


class User(Base):
    __tablename__ = 'user'

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4,
                server_default=text("uuid_generate_v4()"))
    email = Column(String(64), nullable=False)
    password = Column(String(64), nullable=False)
    name = Column(Text, nullable=False)

    tasks = relationship('Task', foreign_keys='[Task.user_id]',
                         back_populates='user')

    __table_args__ = (
        UniqueConstraint('email', name='user_email_constraint'),
        {'schema': 'auth'}
    )
