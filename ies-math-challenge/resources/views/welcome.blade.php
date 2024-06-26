<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Laravel</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-50 flex justify-center items-center h-screen">
  <div class="container mx-auto bg-white p-8 rounded-lg shadow-md flex h-screen">
    <div class="headline flex-3 flex justify-center items-center text-left p-8">
      <div class="mb-8 md:mb-0">
        <h5 class="text-2xl font-bold text-gray-800 mb-4 md:text-left">Welcome to the</h5>
        <h1 class="text-6xl font-bold text-purple-600">International Mathematics</h1>
        <h3 class="text-6xl font-bold text-gray-600">Competition System</h3>
      </div>
    </div>

    <div class="login-form flex-1 p-8 flex justify-center items-center mx-auto bg-purple-200 rounded-lg">
      <form class="flex flex-col items-center w-full max-w-md" method="POST" action="{{ route('login') }}">
        @csrf
        <input type="email" name="email" placeholder="Email" required class="border border-purple-600 rounded-md px-4 py-2 mb-4 w-full max-w-xs">
        <input type="password" name="password" placeholder="Password" required class="border border-purple-600 rounded-md px-4 py-2 mb-4 w-full max-w-xs">
        <button type="submit" class="border border-purple-600 text-purple-600 rounded-md px-4 py-2 transition-colors duration-300 ease-in-out hover:bg-purple-600 hover:text-white w-full max-w-xs">Login</button>
      </form>
    </div>
  </div>
</body>

</html>
