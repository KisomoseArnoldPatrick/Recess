<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Imports\QuestionImport;
use Maatwebsite\Excel\Facades\Excel;

class QuestionController extends Controller
{
  public function importExcelQuestion(Request $request)
  {
    $request->validate([
      'import_file' => [
        'required',
        'file'
      ],
    ]);

    Excel::import(new QuestionImport, $request->file('import_file'));
    return redirect()->back()->with('status', 'Questions Excel Document Imported successfully');

    }

}
