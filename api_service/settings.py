import os
from pydantic_settings import BaseSettings


class ApiSettings(BaseSettings):
    TITLE: str = 'Api Service for Reminder'
    VERSION: str = '0.0'
    DESCRIPTION: str = 'damn cool stonks description'

    DEBUG: bool = os.environ.get('DEBUG', True)
    TIME_ZONE: str = 'UTC'

    DATETIME: str = os.environ.get('DATETIME', '%Y-%m-%dT%H:%M:%S.%f%z')
    URL: str = os.environ.get('URL', '')

    POSTGRES_CONNECT: str = os.environ['POSTGRES_CONNECT']
    SCHEMES: list = ('auth', 'tasks', 'logger')
    POOL: int = os.environ.get('POOL', 50)
    MAX_OVER: int = os.environ.get('MAX_OVER', 100)

    ORIGINS: list = ['*', 'http:/localhost']
    ALLOW_CREDENTIALS: bool = True
    ALLOW_METHODS: list = ['*']
    ALLOW_HEADERS: list = ['*']

    STATIC_FILES: str = os.environ.get('STATIC_FILES', '/code/static')

    REDIS_CONNECT: str = os.environ['REDIS_CONNECT']
    REDIS_MAX: int = int(os.getenv('REDIS_MAX', 100))

    ACCESS_TOKEN_EXPIRE_MINUTES: int = int(os.getenv('ACCESS_TOKEN_EXPIRE_MINUTES:', 30))
    ALGORITHM: str = os.environ.get('ALGORITHM', '"HS256"')
    SECURITY_KEY: str = os.environ['SECURITY_KEY']

    class Config:
        case_sensitive = True


api_settings = ApiSettings()
