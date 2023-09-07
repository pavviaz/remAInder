import uvicorn

if __name__ == "__main__":
    uvicorn.run(
        "main:api_service", host="10.128.0.27", port=7001, log_level="info", reload=True, workers=1,
        reload_dirs=['/code', '/base'],
    )
