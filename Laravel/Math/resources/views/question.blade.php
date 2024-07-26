<!DOCTYPE html>
<html>
<head>
    <title>Import Questions</title>
</head>
<body>
    @if(session('success'))
        <p>{{ session('success') }}</p>
    @endif

    <form action="{{ route('questions.import') }}" method="POST" enctype="multipart/form-data">
        @csrf
        <input type="file" name="file" required>
        <button type="submit">Import Questions</button>
    </form>
</body>
</html>
