import asyncio

from redis.asyncio import Redis
# import pickle

from CacheToolsUtils import PrefixedRedisCache
from settings import api_settings

redis = Redis.from_url(api_settings.REDIS_CONNECT, max_connections=api_settings.REDIS_MAX)

# def redis_cache(ttl: int, key):
#     def dec_wrapper(func):
#         async def wrapper(*args, **kwargs):
#             value = await redis.get(key)
#             if value:
#                 value = pickle.loads(value)
#
#             if not value:
#                 value = await func(*args, **kwargs)
#                 if value:

#                     await redis.set(key, pickle.dumps(value))
#                     await redis.expire(key, ttl)
#
#             return value
#         return wrapper
#     return dec_wrapper
