var builder = WebApplication.CreateBuilder(args);

// �������� ������� � ���������.

builder.Services.AddControllers();
// ��������� � ��������� Swagger/OpenAPI ����� ������ �� ����� https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// ��������� �������� HTTP-��������.
//if (app.Environment.IsDevelopment())
//{
    app.UseSwagger();
    app.UseSwaggerUI();
//}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
