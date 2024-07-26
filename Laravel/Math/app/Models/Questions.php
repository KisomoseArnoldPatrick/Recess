<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
  protected $table = 'questions';
  public $timestamps = false;
  protected $fillable = ['questionId', 'questionText'];


  // public function answer()
  // {
  //   return $this->hasOne(Answer::class); // Defines a one-to-one relationship with the Answer model
//}
}
