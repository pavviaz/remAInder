from settings import api_settings
from sqlalchemy import text
from sqlalchemy.ext.asyncio import AsyncSession, create_async_engine, async_sessionmaker

postgres_engine = create_async_engine(
    api_settings.POSTGRES_URL,
    pool_pre_ping=True,
    pool_recycle=60 * 60,
    pool_size=api_settings.POOL,
    max_overflow=api_settings.MAX_OVER,
    echo=api_settings.DEBUG
)


def get_async_session():
    return async_sessionmaker(
        postgres_engine, class_=AsyncSession, expire_on_commit=False,
    )


async def session_to_receive() -> AsyncSession:
    session = get_async_session()
    async with session() as session_connect:
        try:
            yield session_connect
        finally:
            await session_connect.close()


# async def session_to_commit() -> AsyncSession:
#     session = get_async_session()
#     async with session() as session_connect:
#         try:
#             yield session_connect
#             await session_connect.commit()
#         finally:
#             await session_connect.rollback()
#             await session_connect.close()
