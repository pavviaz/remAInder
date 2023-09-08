import uuid
from typing import Optional

from fastapi import Request
from fastapi_users import BaseUserManager, UUIDIDMixin
from models.user import User
from settings import api_settings


class UserManager(UUIDIDMixin, BaseUserManager[User, uuid.UUID]):
    reset_password_token_secret = api_settings.SECURITY_KEY
    verification_token_secret = api_settings.SECURITY_KEY
    verification_token_audience = api_settings.SECURITY_KEY
