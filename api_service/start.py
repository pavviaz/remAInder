import uvicorn

if __name__ == "__main__":
    uvicorn.run(
        "main:api_service", host="51.250.81.200", port=7001, log_level="info", reload=True, workers=1,
        reload_dirs=['/code', '/base'],
    )
