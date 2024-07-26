<?php

namespace App\Http\Controllers;

use App\Models\pupils;
use App\Http\Requests\StorepupilsRequest;
use App\Http\Requests\UpdatepupilsRequest;
use Illuminate\Http\Request;

class PupilsController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $pupil =pupils::all();
        return view('pupil',compact('pupil'));

    }
    public function create()
    {
        return view('pupils');
    }

    public function Storepupil(Request $request){
        $data =new pupils;
        $data->Username= $request->Username;
        $data->fstname= $request->fstname;
        $data->lstname= $request->lstname;
        $data->DOB=$request->DOB;
        $data->Schregno=$request->Schregno;
        $data->image_file=$request->image_file;
        $data->save();

        $notification = array(
            'message' => '',
            'alert-type' => 'success'
        );

        return redirect()->route('pupils.index')->with($notification);
        
    }
    

    /**
     * Show the form for creating a new resource.
     */
   

    /**
     * Store a newly created resource in storage.
     */
    public function store(StorepupilsRequest $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(pupils $pupils)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(pupils $pupils)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdatepupilsRequest $request, pupils $pupils)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(pupils $pupils)
    {
        //
    }
}
