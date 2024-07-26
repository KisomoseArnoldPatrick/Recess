<?php

use App\Http\Controllers\ChallengeController;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\SchoolsController;
use App\Http\Controllers\PupilsController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

use Illuminate\Support\Facades\Redirect;

Route::get('logot',function() {
    auth()->logout();
    Session()->flush();
    return Redirect::to('/');})->name('logout');

Route::resource('schools', SchoolsController::class);

Route::resource('pupils', PupilsController::class);

Route::resource('challenges', ChallengeController::class);

Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

Route::get('/schoolform', function () {
    return view('schoolreg');
})->middleware(['auth', 'verified'])->name('schoolform');

Route::get('/challengeform', function () {
    return view('challenge');
})->middleware(['auth', 'verified'])->name('challengeform');


Route::get('/calendar', function () {
    return view('calendar');
})->middleware(['auth', 'verified'])->name('calendar');

Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});
Route::post('subbs', [SchoolsController::class, 'storeSchool'])->name('subbs');
Route::post('chlgs', [ChallengeController::class, 'storeChallenge'])->name('chlgs');

require __DIR__.'/auth.php';
