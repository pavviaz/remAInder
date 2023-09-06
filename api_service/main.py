from settings import api_settings
from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from fastapi.middleware.cors import CORSMiddleware


api_service = FastAPI(
    title=api_settings.TITLE,
    version=api_settings.VERSION,
    description=api_settings.DESCRIPTION,
    docs_url=api_settings.URL + '/docs',
    redoc_url=None
)

api_service.add_middleware(
    CORSMiddleware,
    allow_origins=api_settings.ORIGINS,
    allow_credentials=api_settings.ALLOW_CREDENTIALS,
    allow_methods=api_settings.ALLOW_METHODS,
    allow_headers=api_settings.ALLOW_HEADERS
)

api_service.mount(api_settings.URL + '/static', StaticFiles(directory=api_settings.STATIC_FILES), name='static')
