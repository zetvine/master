var builder = WebApplication.CreateBuilder(args);

// Добавьте сервисы в контейнер.

builder.Services.AddControllers();
// Подробнее о настройке Swagger/OpenAPI можно узнать на сайте https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Настройте конвейер HTTP-запросов.
//if (app.Environment.IsDevelopment())
//{
    app.UseSwagger();
    app.UseSwaggerUI();
//}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
